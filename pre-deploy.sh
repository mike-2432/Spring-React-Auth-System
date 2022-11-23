# SSL Certificate for a secure HTTPS connection
echo "Checking if SSL Certificate is present for a secure HTTPS connection."
if [ -f "nginx/certs/host.cert" ] && [ -f "nginx/certs/host.key" ]; 
then
    echo "SSL Certificate is present."
else
    echo "SSL Certificate is not present, generating new SSL Certificate."
    cd nginx
    mkdir certs
    sudo chmod ugo+w certs
    cd certs
    sudo openssl genrsa 2048 > host.key
    sudo chmod 400 host.key
    sudo openssl req -new -x509 -nodes -sha256 -days 365 -key host.key -out host.cert
    cd ../..
fi

# RSA Keypair for JWT encryption
echo "Checking if an RSA Keypair folder is present for JWT Encryption."
if [ -d "server/src/main/resources/certs" ] && [ -f "server/src/main/resources/certs/private.pem" ] && [ -f "server/src/main/resources/certs/public.pem" ];
then
    echo "RSA Keypair is present."
else
    echo "Creating RSA Keypair."
    cd server/src/main/resources
    mkdir certs
    sudo chmod ugo+w certs
    cd certs
    sudo openssl genrsa -out keypair.pem 2048
    sudo openssl rsa -in keypair.pem -pubout -out public.pem
    sudo openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
    sudo rm keypair.pem
    cd ../../../..
fi

# Check for docker and install
echo "Checking if Docker-(Compose) is installed."
if [ -x "$(command -v docker)" ] && [ -x "$(command -v docker-compose)" ];
then
    echo "Docker-(Compose) is installed."
else
    echo "Installing Docker and Docker-Compose"
    sudo yum install docker
    sudo usermod -a -G docker ec2-user

    sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
    sudo dockerd
    sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
fi




