import Footer from "../components/Footer";
import { useState, useEffect } from "react";
import { FaUser, FaLaptopCode } from "react-icons/fa";
import { info } from "../data";

// HOME PAGE //
const Home = () => {
  const [scrollA, setScrollA] = useState(0);
  const [scrollB, setScrollB] = useState(0);
  const [scrollC, setScrollC] = useState(0);

  useEffect(() => {
    const handleScroll = () => {
      setScrollA(window.scrollY / 1.5);

      if (window.scrollY < 30) setScrollB(0);
      else setScrollB(window.scrollY / 1.5 - 30);

      if (window.scrollY < 100) setScrollC(0);
      else setScrollC(window.scrollY / 1.5 - 100);
    };

    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  // RETURN //
  return (
    <>
      <div className="page-container">
        {/* Text blocks */}
        <div className="homepage-main">
          <div className="homepage-main-block"style={{ transform: "translate(0, -" + scrollA + "px)" }}>
            <h1>A Security Project</h1>
            <p>A project about Authorization & Authentication</p>
            <div className="homepage-main-block-inner"></div>
          </div>

          <div className="homepage-blocks">
            <div className="homepage-twinblock" style={{ transform: "translate(0, -" + scrollB + "px)" }}>
              <p style={{ paddingBottom: "1rem" }}>Optional User</p>
              <p>Username:</p>
              <p>Password:</p>
              <div className="homepage-twinblock-inner">
                <FaUser />
              </div>
            </div>

            <div className="homepage-twinblock" style={{ transform: "translate(0, -" + scrollC + "px)" }}>
              <p style={{ paddingBottom: "1rem" }}>Used Technologies</p>
              <ul>
                <li>Spring-Boot</li>
                <li>React</li>
                <li>Docker</li>
                <li>Nginx</li>
                <li>AWS</li>
                <li>MySQL</li>
              </ul>
              <div className="homepage-twinblock-inner">
                <FaLaptopCode />
              </div>
            </div>
          </div>
        </div>

        {/* About */}
        <div className="homepage-about">
          <h1>About the Project</h1>
          <div className="underline-thick"></div>
          <div className="homepage-about-text">
            {info.map((paragraph: any, index: number) => {
              return <p key={index}>{paragraph}</p>;
            })}
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default Home;
