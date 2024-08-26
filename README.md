# realtime-wearable-ECG-monitoring

Estimating heart rate and SpO2 for moving patients can be quite challenging due to the sensitivity of ECG and PPG signals to noise caused by sensor malfunctions, breathing, and electrode movements [[1](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib19)], [[2](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib20)]. To obtain accurate measurements, daily activities need to be restricted, and optimal placement of the pulse oximeter at the extremities is crucial for effective SpO2 estimation [[3](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib39)]. The correlation between peripheral blood volume variation and left ventricular myocardial activity makes it possible to establish a relationship between PPG and ECG signals.

Researchers have successfully reconstructed ECG signals from PPG signals using deep neural networks [[4](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib55)], [[5](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib56)], and [[6](https://www.sciencedirect.com/science/article/pii/S0010482524011521#bib57)]. However, these models were not designed to handle noisy signals, which limited their effectiveness.

To address this issue, we developed a wearable system coupled with a deep learning model that can reconstruct noise-free ECG and PPG signals from noisy ECG signals, as described in [[7](https://www.sciencedirect.com/science/article/pii/S0010482524011521)]. An Android application powered by the trained model enables real-time reconstruction of these signals, which are then used to estimate heart rate and SpO2.
