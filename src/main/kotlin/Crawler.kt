import com.google.gson.Gson
import io.github.bonigarcia.wdm.WebDriverManager
import kotlinx.coroutines.*
import net.lightbody.bmp.BrowserMobProxy
import net.lightbody.bmp.BrowserMobProxyServer
import net.lightbody.bmp.client.ClientUtil
import net.lightbody.bmp.proxy.CaptureType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.devtools.DevTools
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.devtools.v85.network.Network
import java.util.*
import okhttp3.Headers
import org.openqa.selenium.chrome.ChromeOptions
import java.util.concurrent.TimeUnit
import kotlin.coroutines.suspendCoroutine
import org.openqa.selenium.devtools.v85.network.model.Headers as SeleniumHeaders

fun main() {

    // Define the base URL and temporary URL
    val BASE_URL = "http://www.playphrase.me"
    val TEMP_URL = "https://translate.google.com/"

    // Configure ChromeOptions to set up the Chrome browser
    val options = ChromeOptions().apply {
        addArguments("--allowed-ips")  // Allow access to specific IPs
        setAcceptInsecureCerts(true)   // Accept insecure SSL certificates

        // The following lines are currently commented out and can be used to run the browser in headless mode (without a visible window).
        // addArguments("--headless")
        // addArguments("--disable-gpu")
        // addArguments("--no-sandbox")
    }

    // Create an instance of ChromeDriver with the specified options
    val driver: WebDriver = ChromeDriver(options)

    // Create a session for DevTools (Chrome Developer Tools)
    val devTools: DevTools = (driver as ChromeDriver).devTools
    devTools.createSession()

    // Enable the Network feature in DevTools to monitor network requests
    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()))

    // Add a listener to monitor outgoing network requests
    devTools.addListener(Network.requestWillBeSent()) { request ->
        // Check if the request URL contains the phrase "phrases/search?q="
        if (request.request.url.toString().contains("phrases/search?q=")) {

            // Convert Selenium headers to OkHttp headers
            val seleniumHeaders = request?.request?.headers
            val okhttpHeaders = convertHeaders(seleniumHeaders)

            // Create an OkHttp client to send HTTP requests
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                .url(request.request.url.toString())
                .headers(okhttpHeaders)
                .build()

            try {
                // Send the request and receive the response
                val call = client.newCall(request)
                val response = call.execute()

                // Convert the JSON response into an object of type MRes using the Gson library
                var data = Gson().fromJson(response.body?.string(), MRes::class.java)

                // Iterate through the list of phrases and print the text and video URL
                for (it in data.phrases!!) {
                    println(it?.text)
                    println(it?.videoUrl)
                    TimeUnit.SECONDS.sleep(2)  // Add a 2-second delay between each request
                    val json = Gson().toJson(it)
                }

                // Redirect the browser to the temporary URL and then back to the base URL
                driver.get(TEMP_URL)
                TimeUnit.SECONDS.sleep(2)
                driver.get(BASE_URL)

            } catch (e: Exception) {
                // In case of an error, redirect the browser to the temporary URL and then back to the base URL
                driver.get(TEMP_URL)
                TimeUnit.SECONDS.sleep(2)
                driver.get(BASE_URL)
            }
        }
    }

    // Redirect the browser to the base URL
    driver.get(BASE_URL)

    // Read a line of input (this part is currently inactive and can be used to take input from the user)
    val string1 = readLine()!!
}