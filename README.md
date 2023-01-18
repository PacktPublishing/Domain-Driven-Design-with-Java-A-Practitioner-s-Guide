


# Domain-Driven Design with Java - A Practitioner’s Guide

<a href="https://www.packtpub.com/programming/domain-driven-design-with-java-a-practitioner-s-guide?utm_source=github&utm_medium=repository&utm_campaign=9781800560734"><img src="https://static.packt-cdn.com/products/9781800560734/cover/smaller" alt="Domain-Driven Design with Java - A Practitioner’s Guide" height="256px" align="right"></a>

This is the code repository for [Domain-Driven Design with Java - A Practitioner’s Guide](https://www.packtpub.com/programming/domain-driven-design-with-java-a-practitioner-s-guide?utm_source=github&utm_medium=repository&utm_campaign=9781800560734), published by Packt.

**Create simple, elegant, and valuable software solutions for complex business problems**

## What is this book about?
Domain-Driven Design (DDD) makes available a set of techniques and patterns that enable domain experts, architects, and developers to work together to decompose complex business problems into a set of well-factored, collaborating, and loosely coupled subsystems. 

This book covers the following exciting features:
* Discover how to develop a shared understanding of the problem domain
* Establish a clear demarcation between core and peripheral systems
* Identify how to evolve and decompose complex systems into well-factored components
* Apply elaboration techniques like domain storytelling and event storming
* Implement EDA, CQRS, event sourcing, and much more
* Design an ecosystem of cohesive, loosely coupled, and distributed microservices
* Test-drive the implementation of an event-driven system in Java
* Grasp how non-functional requirements influence bounded context decompositions

If you feel this book is for you, get your [copy](https://www.amazon.com/dp/1800560737) today!

<a href="https://www.packtpub.com/?utm_source=github&utm_medium=banner&utm_campaign=GitHubBanner"><img src="https://raw.githubusercontent.com/PacktPublishing/GitHub/master/GitHub.png" 
alt="https://www.packtpub.com/" border="5" /></a>

## Instructions and Navigations
All of the code is organized into folders. For example, Chapter02.

The code will look like the following:
```
interface PasswordService {
    String generateStrongPassword();
    boolean isStrong(String password);
    boolean isWeak(String password);
}
```

**Following is what you need for this book:**
This book is for intermediate Java programmers looking to upgrade their software engineering skills and adopt a collaborative and structured approach to designing complex software systems. Specifically, the book will assist senior developers and hands-on architects to gain a deeper understanding of domain-driven design and implement it in their organization. Familiarity with DDD techniques is not a prerequisite; however, working knowledge of Java is expected.

## Errata

* Page 28: Figure 1.21 is incorrect and the correct image is as follows: 
<img src="https://github.com/PacktPublishing/Domain-Driven-Design-with-Java-A-Practitioner-s-Guide/blob/master/value-object-example.png">

## Pre-Requisites
* JDK 14
* Maven 3.3+

## Usage

Convert the AsciiDoc to HTML5 by invoking the `process-resources` goal (configured as the default goal):

 $ mvn

Open the file _target/generated-docs/main.html_ in your browser to see the generated HTML file containing the generated diagram images.

## Graphviz configuration
Asciidoctor Diagram bundles both the ditaa and PlantUML libraries and will use them to generate diagrams.
In order to generate diagrams using Graphviz, you must install it separately.
There are two options to reference the installed Graphviz's _dot_ tool in order to generate diagrams: system's PATH or plug-in attributes configuration.

### Configuration via system's PATH
Visit [Graphviz' site](http://www.graphviz.org/) for details on how to install the _dot_ command tool, and to make the _dot_ command available on your system's PATH.

With the following software and hardware list you can run all code files present in the book (Chapter 1-12).
### Software and Hardware List
| Chapter | Software required | OS required |
| -------- | ------------------------------------ | ----------------------------------- |
| 1-12 | Spring Framework | Windows/Linux/macOS |
| 1-12 | Axon Framework | Windows/Linux/macOS |
| 1-12 | JavaFX | Windows/Linux/macOS |

We also provide a PDF file that has color images of the screenshots/diagrams used in this book. [Click here to download it](https://packt.link/TwzEB).

### Related products
* AWS Penetration Testing [[Packt]](https://www.packtpub.com/product/aws-penetration-testing/9781839216923?utm_source=github&utm_medium=repository&utm_campaign=9781839216923) [[Amazon]](https://www.amazon.com/dp/1839216921)

* Learn Kali Linux 2019 [[Packt]](https://www.packtpub.com/free-ebook/learn-kali-linux-2019/9781789611809?utm_source=github&utm_medium=repository&utm_campaign=9781789611809) [[Amazon]](https://www.amazon.com/dp/1789611806)

## Get to Know the Authors
**Premanand (Prem) Chandrasekaran**
 is a technology leader and change agent with a solid track record of leading large technology teams and helping businesses deliver mission critical problems while exhibiting high internal and external quality. In the past two decades, he has had the pleasure of helping a variety of clients and domains ranging from financial services, online retailers, education, healthcare startups among others. His specialties include technical innovation, architecture, continuous delivery, agile/iterative transformation and employee development. When not fiddling with his trusty laptop, he spends time with his son ripping beyblades, playing video games and analyzing the nuances of cricket.

**Karthik Krishnan**
 is a technology leader with over 25 years of experience in designing and building large-scale enterprise solutions across financial and retail domains. He has played numerous technical roles in leading product development for major financial institutions. He is currently serving the role of Technical Principal at Thoughtworks. He is passionate about platform thinking, solution architecture, application security and strives to be known as a coding architect. His most recent assignment entailed leading a large technology team helping their clients in their legacy modernization journey with Cloud. When not working, he spends time practicing playing tunes on his musical keyboard.
### Download a free PDF

 <i>If you have already purchased a print or Kindle version of this book, you can get a DRM-free PDF version at no cost.<br>Simply click on the link to claim your free PDF.</i>
<p align="center"> <a href="https://packt.link/free-ebook/9781800560734">https://packt.link/free-ebook/9781800560734 </a> </p>