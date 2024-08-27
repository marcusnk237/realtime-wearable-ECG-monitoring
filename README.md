# realtime-wearable-ECG-monitoring
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



### sofware built With

[![Python][Python]][Python-url]
[![Tensorflow][Tensorflow]][Tensorflow-url]
[![Android][Android]][android-url]

### Hardware
[![Arduino][Arduino]][android-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites
[![Tensorflow][Tensorflow]][Tensorflow-url]
[![Numpy][Numpy]][Numpy-url]

### Installation

Clone the repo
   ```
   sh git clone https://github.com/marcusnk237/realtime-wearable-ECG-monitoring.git
   ```
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Usage
```

compute_cam_1d_output (model, data , layer_name , N)
Arguments:
> - model : The model trained
> - data : The data sample
> - layer_name : The last layer of the feature extraction part of the model. Usually, it is the last layer before the Flattening operation.
> - N : data length
```
![Alt text](https://github.com/marcusnk237/dataset_gradcam_plus_plus/blob/main/results/gradcam_plus_plus_1d.png)


<!-- CONTACT -->
## Contact
* [![LinkedIn][linkedin-shield]][linkedin-url]

* Project Link: [https://github.com/marcusnk237/realtime-wearable-ECG-monitoring](https://github.com/marcusnk237/realtime-wearable-ECG-monitoring)
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

The authors of the original article about GRAD-CAM++
* [Aditya Chattopadhay; Anirban Sarkar; Prantik Howlader; Vineeth N Balasubramanian : Grad-CAM++: Generalized Gradient-Based Visual Explanations for Deep Convolutional Networks](https://doi.org/10.1109/WACV.2018.00097)
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/marcusnk237/dataset_gradcam_plus_plus/blob/main/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/marc-junior-nkengue/
[product-screenshot]: images/screenshot.png

[Opencv]:https://img.shields.io/badge/opencv-%23white.svg?style=for-the-badge&logo=opencv&logoColor=white
[Opencv-url]:https://pypi.org/project/opencv-python/
[Matplotlib]:https://img.shields.io/badge/Matplotlib-%23ffffff.svg?style=for-the-badge&logo=Matplotlib&logoColor=black
[Matplotlib-url]:https://matplotlib.org/
[NumPy]: https://img.shields.io/badge/numpy-%23013243.svg?style=for-the-badge&logo=numpy&logoColor=white
[Numpy-url]:https://numpy.org/
[Python]: https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=yellow
[Python-url]: https://www.python.org/
[Tensorflow]: https://img.shields.io/badge/TensorFlow-FF6F00?style=for-the-badge&logo=tensorflow&logoColor=white 
[Tensorflow-url]:  https://www.tensorflow.org/
[Arduino]: https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrbcHJkJJ0cIU-Q_Bp_5s6_AnWLLMQuqs3NQ&s
[Arduino-url]: https://docs.arduino.cc/hardware/nano-33-ble-sense/
[Android]: https://lh3.googleusercontent.com/LYUDWiiqyTSiwzbPsJnYhfTzA3kUAoYgRy_1mpKTZOuLtpaMTaNdPKm8Xesm5mxA_zUSIGy6RO4PxhUnIDgTgbmroxgVpudnc0XKWW0cByZXppI2WGo
[Android-url]: https://www.android.com/
[C-icon]:https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvYlRhZ9q1WopmD3kxjy_-jq9k5rLP0Q_-WA&s
[C-url]:https://cplusplus.com/
[Java]: https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9SixnYxC_OiSUivLvK8KpOZhSwngrZfKYhQ&s
[Java-url]:https://www.java.com/


