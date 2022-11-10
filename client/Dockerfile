FROM node:19-alpine

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . ./
EXPOSE 3000
CMD ["npm", "start"]

# docker network create auth_app_network
# docker build -t auth_app:latest .
# docker run -it -d --rm --name auth_app --network auth_app_network -v $(pwd):/app -p 3000:3000 auth_app:latest

