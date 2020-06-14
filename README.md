<img src="https://raw.githubusercontent.com/Asnkngt/Slang/master/slang.png">

# Overview
* Slang is an Android app to help users learn American Sign Language
* Slang requires [the backend](https://github.com/slang-senior-design/backend) to be deployed to the cloud before it can run
locally
* The dataset `clean-dataset.csv` is stored in the backend
* Check out our [video demo](https://www.youtube.com/watch?v=8xhHSoFg5s0)

# Assumptions
* Latest version of Git installed
* Latest *stable* version of Android Studio installed (v3.6.3 as of writing this doc)
* Emulator or phone running Android Q (v10.0, Android API 29)
* Ideally Windows 10 environment to run into less issues but other operating systems should
work fine

# Running the app
1. Deploy the backend to AWS Elastic Beanstalk:
    1. Clone [the backend](https://github.com/slang-senior-design/backend)
    2. Follow [these instructions](https://github.com/slang-senior-design/backend/blob/master/README.md)
2. Clone the frontend (this directory)
3. Open Android Studio, select `Open an existing Android Studio Project`, and navigate to the Slang project to open it
4. If using your own backend, open the file `APIClient.java` and replace the url on line 23 with the url your AWS server
5. Run the app using a real device OR an emulator by following [this guide](https://developer.android.com/training/basics/firstapp/running-app)
