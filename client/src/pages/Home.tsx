import Footer from '../components/Footer';
import hero from '../images/hero.jpg';

// HOME PAGE //
const Home = () => {
    return (
        <>
            <div className="page-container">

                <div className="homepage-hero">
                    <img className="hero-img" src={hero} alt="desk by Leone Venter"></img>
                    <div className="hero-overlay"></div>
                    <div className="hero-text">
                        <div className="hero-text-overlay"></div>
                        <h1>A Security Project</h1>
                        <div className="hero-underline"></div>
                        <p>A project about Authentication and Authorization</p>
                    </div>                   
                </div>

                <div className="homepage-about">
                    <h1>About the Project</h1>
                    <div className="homepage-about-text">
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Quaerat vero laborum, animi iste delectus rerum dignissimos doloremque ab, exercitationem harum fugit, qui accusantium sint. Quas obcaecati quae veritatis quos corporis.</p>
                        <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Provident, odit amet minus laborum nesciunt vero beatae sint voluptas ea facere, quasi mollitia! Soluta laudantium voluptates inventore eum voluptatum laboriosam odio.</p>
                        <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Eligendi, modi voluptatem, in reiciendis quasi tempora nihil temporibus vero quam at rem veritatis ad. Maiores doloribus itaque est eveniet praesentium? Consequuntur!</p>
                    </div>
                </div>
            </div>
            <Footer />
        </>
    )
}

export default Home