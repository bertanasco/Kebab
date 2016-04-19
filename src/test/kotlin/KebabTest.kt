package kebab.junit4

import kebab.Browser
import kebab.Configuration
import kebab.configuration
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class KebabTest {
    val kebabConfEnv: String by Delegates.notNull()
    val kebabConfScript: String by Delegates.notNull()
    var browser: Browser by Delegates.notNull<Browser>()
    val config: Configuration by lazy {
        configuration {
            baseUrl = "http://www.google.co.jp/"

            driver = ChromeDriver()

            options {
                timeout {
                    implicitlyWait = 10L to TimeUnit.SECONDS
                }
            }
        }
    }

    init {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver")
    }


    @Before
    fun setup() {
        browser = Browser(config)
    }

    @After
    fun teardown() {
        config.driver.quit()
    }

    @Ignore
    @Test
    fun test() {
        browser.drive("http://www.google.co.jp/", {
            // 画面が表示されていること
            assertEquals("Google", title)
            // TODO ここらへん出来てない
            // 検索ボタンを押下
            val searchButton = find(By.cssSelector(".jsb > center:nth-child(1) > input:nth-child(1)"))
            // 検索を実行する
            searchButton.click()
        })
        browser.quit()
    }
}
