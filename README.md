
# PlayPhrase Crawler

This project is a **web crawler** designed to interact with the [PlayPhrase.me](http://www.playphrase.me) website. It uses **Selenium** and **ChromeDriver** to automate browser actions and **OkHttp** to handle HTTP requests. The crawler monitors network requests, extracts specific data (such as phrases and video URLs), and processes the responses for further use.

## Features

- **Automated Browser Interaction**: Uses Selenium and ChromeDriver to navigate the PlayPhrase.me website.
- **Network Request Monitoring**: Utilizes Chrome DevTools to intercept and analyze network requests.
- **Data Extraction**: Extracts phrases and their corresponding video URLs from the website.
- **Customizable Configuration**: Allows you to configure browser options, such as running in headless mode or accepting insecure SSL certificates.

## How It Works

1. **Browser Setup**: The crawler initializes a Chrome browser instance with specific options (e.g., allowing certain IPs, accepting insecure certificates).
2. **Network Monitoring**: It uses Chrome DevTools to monitor network requests and intercepts requests containing the phrase `"phrases/search?q="`.
3. **Data Processing**: When a matching request is found, the crawler sends an HTTP request using OkHttp, processes the JSON response, and extracts relevant data (e.g., phrases and video URLs).
4. **Redirection**: After processing, the browser is redirected to a temporary URL (e.g., Google Translate) and then back to the PlayPhrase.me website to simulate user interaction.
5. **Error Handling**: If an error occurs during the process, the crawler redirects the browser to the temporary URL and then back to the base URL to ensure continuity.

## Technologies Used

- **Selenium**: For browser automation.
- **ChromeDriver**: To control the Chrome browser.
- **OkHttp**: For sending and handling HTTP requests.
- **Gson**: For parsing JSON responses.
- **Kotlin**: The programming language used to write the crawler.

## Usage

1. Clone the repository:
   ```bash
   git clone https://github.com/mehrdadmmb2/PlayPhrase-Crawler.git
## Donate
***Bitcoin*:**

    bc1qxk0h9rdpgnh7yyc59uxndkrr2ndjglwtv3z72j

***USDT:***

    TRzxqih3wSjPkb8EmrF7TzAvSquGhf1wwo



