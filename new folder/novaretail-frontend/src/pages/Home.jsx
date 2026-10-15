import { Link } from "react-router-dom";

const highlights = [
    { number: "01", title: "Find your next favorite", text: "Explore a considered collection of everyday essentials." },
    { number: "02", title: "Shop with confidence", text: "Clear product details and ratings help you choose well." },
    { number: "03", title: "A little more personal", text: "Sign in to keep your shopping bag close at hand." },
];

function Home() {
    return (
        <div className="home-page">
            <section className="hero-section">
                <div className="hero-copy">
                    <span className="eyebrow"><span className="eyebrow-dot" /> THE EVERYDAY, REIMAGINED</span>
                    <h1>Good things.<br /><span>Better finds.</span></h1>
                    <p>A thoughtfully picked collection for the moments, rituals, and little upgrades that make a day.</p>
                    <div className="hero-actions">
                        <Link className="button button-dark" to="/products">Explore the collection <span aria-hidden="true">↗</span></Link>
                        <span className="hero-note">New discoveries, always.</span>
                    </div>
                    <div className="hero-proof">
                        <div className="proof-avatars" aria-hidden="true"><span>J</span><span>M</span><span>A</span></div>
                        <span><strong>Made for real life</strong><br />Loved by curious shoppers</span>
                    </div>
                </div>
                <div className="hero-art" aria-label="Abstract illustration of a curated shopping bag">
                    <div className="art-sun" />
                    <div className="art-orbit art-orbit-one" />
                    <div className="art-orbit art-orbit-two" />
                    <div className="art-sticker">A LITTLE<br />SOMETHING<br />GOOD</div>
                    <div className="art-card">
                        <div className="art-card-top"><span>NR</span><span>CURATED GOODS</span></div>
                        <div className="art-flower" aria-hidden="true"><i /><i /><i /><i /><i /><i /><b /></div>
                        <div className="art-card-bottom"><span>OBJECTS<br />FOR EVERYDAY</span><span>NO. 01<br />EST. 2024</span></div>
                    </div>
                    <div className="art-caption">YOUR NEXT<br />GOOD THING <span>✳</span></div>
                </div>
                <div className="hero-index"><span>01</span><span className="index-line" /><span>03</span></div>
            </section>

            <section className="benefit-strip" aria-label="Shopping benefits">
                <div><span className="benefit-icon">✳</span><span><strong>Considered collection</strong><small>Chosen with care</small></span></div>
                <div><span className="benefit-icon">↗</span><span><strong>Easy to explore</strong><small>Find what feels right</small></span></div>
                <div><span className="benefit-icon">♡</span><span><strong>Made for everyday</strong><small>Little things, big joy</small></span></div>
            </section>

            <section className="intro-section">
                <div>
                    <span className="eyebrow">A BETTER WAY TO BROWSE</span>
                    <h2>Good things have a way<br />of finding their people.</h2>
                </div>
                <p>From the useful to the unexpectedly delightful, discover pieces picked to bring a little more intention to your everyday.</p>
            </section>
            <section className="highlight-grid">
                {highlights.map((item) => (
                    <article className="highlight-card" key={item.number}>
                        <span>{item.number}</span>
                        <h3>{item.title}</h3>
                        <p>{item.text}</p>
                    </article>
                ))}
            </section>
            <section className="bottom-cta">
                <div><span className="eyebrow">YOUR NEXT FAVORITE IS OUT THERE</span><h2>Ready to find it?</h2></div>
                <Link className="button button-dark" to="/products">Browse the shop <span aria-hidden="true">↗</span></Link>
            </section>
        </div>
    );
}

export default Home;
