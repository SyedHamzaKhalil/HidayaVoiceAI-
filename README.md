# 🎙️ Hidaya Voice AI

A modern web application that uses AI-powered fingerprinting technology to recognize Quranic verses from audio recitations. Users can recite Quranic verses into their microphone and get instant identification of the Surah and Ayah numbers.

## ✨ Features

- **Real-time Voice Recognition**: Capture audio directly from the microphone
- **AI-Powered Fingerprinting**: Advanced audio fingerprinting for precise verse identification
- **Beautiful Modern UI**: Responsive design with smooth animations and intuitive interface
- **Instant Results**: Quick processing and identification of Quranic verses
- **Cross-platform**: Works on desktop and mobile browsers

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Gradle 7.0 or higher
- Modern web browser with microphone support

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd HidayaVoiceAI
   ```

2. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

3. **Access the application**
   Open your browser and navigate to: `http://localhost:8080`

## 🎯 How to Use

1. **Open the Application**: Navigate to the landing page in your browser
2. **Grant Microphone Permission**: Click "Allow" when prompted for microphone access
3. **Start Recording**: Click the microphone button to begin recording
4. **Recite a Quranic Verse**: Speak clearly into your microphone
5. **Stop Recording**: Click the microphone button again to stop
6. **View Results**: The application will display the identified Surah and Ayah number

## 🏗️ Architecture

### Backend (Spring Boot)
- **AyahFingerPrintService**: Core service for audio fingerprinting and matching
- **VoiceRecognitionController**: REST API endpoints for voice processing
- **HomeController**: Serves the frontend landing page

### Frontend (HTML/CSS/JavaScript)
- **Modern UI**: Responsive design with gradient backgrounds and smooth animations
- **Web Speech API**: Real-time speech recognition
- **MediaRecorder API**: Audio capture and processing
- **Fetch API**: Communication with backend services

## 🔧 API Endpoints

- `GET /` - Landing page
- `POST /api/voice/recognize` - Process audio and identify Quranic verse
- `GET /api/voice/health` - Health check endpoint

## 🛠️ Technical Details

### Audio Processing
- Uses MediaRecorder API for audio capture
- Supports WAV format for backend processing
- Real-time audio streaming and processing

### Fingerprinting Algorithm
- Euclidean distance calculation for fingerprint comparison
- Byte array processing for audio fingerprint matching
- Optimized for Arabic Quranic recitation patterns

### Browser Compatibility
- Chrome/Chromium (recommended)
- Firefox
- Safari (limited support)
- Edge

## 📱 Mobile Support

The application is fully responsive and works on mobile devices:
- Touch-friendly interface
- Optimized for mobile browsers
- Responsive design adapts to screen size

## 🔒 Security & Privacy

- Audio processing happens locally in the browser
- No audio data is stored permanently
- Temporary files are cleaned up after processing
- CORS enabled for development

## 🐛 Troubleshooting

### Microphone Not Working
- Ensure your browser has permission to access the microphone
- Check that your microphone is properly connected and working
- Try refreshing the page and granting permissions again

### No Recognition Results
- Speak clearly and at a normal volume
- Ensure you're reciting a Quranic verse
- Check that the audio quality is good

### Browser Compatibility Issues
- Use Chrome or Chromium for best compatibility
- Ensure you're using a modern browser version
- Check that JavaScript is enabled

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Built with Spring Boot and modern web technologies
- Inspired by the need for accurate Quranic verse recognition
- Uses advanced audio fingerprinting techniques for precise identification 