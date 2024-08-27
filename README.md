<a name="readme-top"></a>
<br />
<div align="center">
  <h1 align="center">Wearable system for real-time reliable ECG monitoring</h1>

</div>
<!-- TABLE OF CONTENTS -->
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-code">About</a>
      <ul>
        <li><a href="#built-with">Software built With</a></li>
        <li><a href="#hardware">Hardware</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>


<!-- ABOUT THE PROJECT -->
## About The Project


Estimating heart rate and SpO2 for moving patients can be quite challenging due to the sensitivity of ECG and PPG signals to noise caused by sensor malfunctions, breathing, and electrode movements [[1](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib19)], [[2](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib20)]. To obtain accurate measurements, daily activities need to be restricted, and optimal placement of the pulse oximeter at the extremities is crucial for effective SpO2 estimation [[3](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib39)]. The correlation between peripheral blood volume variation and left ventricular myocardial activity makes it possible to establish a relationship between PPG and ECG signals.

Researchers have successfully reconstructed ECG signals from PPG signals using deep neural networks [[4](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib55)], [[5](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib56)], and [[6](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib57)]. However, these models were not designed to handle noisy signals, which limited their effectiveness.

To address this issue, we developed a wearable system coupled with a deep learning model that can reconstruct noise-free ECG and PPG signals from noisy ECG signals, as described in [[7](https://www.sciencedirect.com/science/article/pii/S0010482524011521)]. An Android application powered by the trained model enables real-time reconstruction of these signals, which are then used to estimate heart rate and SpO2.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Sofware built With

[![Python][Python]][Python-url]
[![Tensorflow][Tensorflow]][Tensorflow-url]
[![Java][Java]][Java-url]
[![Android][Android]][Android-url]
[![C-icon][C-icon]][C-url]

### Hardware
[![Arduino][Arduino]][Arduino-url]
[![ECG][ECG]][ECG-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites
[![ArduinoIDE][ArduinoIDE]][ArduinoIDE-url]
[![Androidstudio][Androidstudio]][Androidstudio-url]

### Installation

> - Clone the repo
   ```
   sh git clone https://github.com/marcusnk237/realtime-wearable-ECG-monitoring.git
   ```
> - Upload the C++ program to your Arduino Board using Arduino IDE
> - Install the android application using Android Studio
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Usage
```
> - After wearing the wearable system , the user have to launch the android application.
> - Click to connect to device button. The application will scan every peripheral BLE nearby.
> - The user must connect the application to a peripheral named ENSAITHealthMonitorDevice
```
<!-- CONTACT -->
## Contact
* [![LinkedIn][linkedin-shield]][linkedin-url]

* Project Link: [https://github.com/marcusnk237/realtime-wearable-ECG-monitoring](https://github.com/marcusnk237/realtime-wearable-ECG-monitoring)
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

The authors of the original article 
* [Marc Junior Nkengue,Xianyi Zeng,Ludovic Koehl,Xuyuan Tao,François Dassonville,Nicolas Dumont,Shixin Ye-Lehmann,Yvette Akwa,Hanwen Ye : An intelligent garment for long COVID-19 real-time monitoring](https://doi.org/10.1016/j.compbiomed.2024.109067)
<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/marc-junior-nkengue/
[product-screenshot]: images/screenshot.png

[Python]: https://cdn3.iconfinder.com/data/icons/logos-and-brands-adobe/512/267_Python-512.png
[Python-url]: https://www.python.org/
[Tensorflow]: https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQEsqbuvRgmIsxTT1R_bCdv8txFKkw2ylx5Lg&s 
[Tensorflow-url]:  https://www.tensorflow.org/
[Arduino]: https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrbcHJkJJ0cIU-Q_Bp_5s6_AnWLLMQuqs3NQ&s
[Arduino-url]: https://docs.arduino.cc/hardware/nano-33-ble-sense/
[Android]: https://lh3.googleusercontent.com/LYUDWiiqyTSiwzbPsJnYhfTzA3kUAoYgRy_1mpKTZOuLtpaMTaNdPKm8Xesm5mxA_zUSIGy6RO4PxhUnIDgTgbmroxgVpudnc0XKWW0cByZXppI2WGo
[Android-url]: https://www.android.com/
[C-icon]:https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvYlRhZ9q1WopmD3kxjy_-jq9k5rLP0Q_-WA&s
[C-url]:https://cplusplus.com/
[Java]: https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9SixnYxC_OiSUivLvK8KpOZhSwngrZfKYhQ&s
[Java-url]:https://www.java.com/
[ECG]: https://cdn.sparkfun.com/assets/parts/9/3/4/4/12650-01.jpg
[ECG-url]:https://www.sparkfun.com/products/12650

[ArduinoIDE]: https://content.arduino.cc/assets/arduino_logo_1200x630-01.png
[ArduinoIDE-url]:https://www.arduino.cc/en/software

[Androidstudio]: https://linuxtutoriels.wordpress.com/wp-content/uploads/2017/06/android-studio-logo.png
[Androidstudio-url]:https://developer.android.com/studio


