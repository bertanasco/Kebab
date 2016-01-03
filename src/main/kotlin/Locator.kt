package kebab

import kebab.BrowserBackedNavigatorFactory
import kebab.Locator
import kebab.Navigator
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

/**
 * Created by yy_yank on 2015/12/30.
 */
class DefaultLocator(val locator: SearchContextBasedBasicLocator) : Locator {

    // TODO UnsupportedOperationExceptionの山

    override fun find(bySelector: By): Navigator = locator.find(bySelector)

    override fun find(attributes: MutableMap<String, Any>, selector: String) = locator.find(attributes, selector)

    override fun find(selector: String) = find(By.cssSelector(selector))

    override fun find(attributes: MutableMap<String, Any>) = find(attributes, MATCH_ALL_SELECTOR)

    override fun find(selector: String, index: Int): Navigator {
        throw UnsupportedOperationException()
    }

    override fun find(selector: String, range: ClosedRange<Int>): Navigator {
        throw UnsupportedOperationException()
    }

    override fun find(attributes: MutableMap<String, Any>, bySelector: By) = find(bySelector).filter(attributes)

    override fun find(attributes: MutableMap<String, Any>, bySelector: By, index: Int): Navigator {
        throw UnsupportedOperationException()
    }

    override fun find(attributes: MutableMap<String, Any>, bySelector: By, range: ClosedRange<Int>): Navigator {
        throw UnsupportedOperationException()
    }

    override fun find(bySelector: By, index: Int): Navigator {
        throw UnsupportedOperationException()
    }

    override fun find(bySelector: By, range: ClosedRange<Int>): Navigator {
        throw UnsupportedOperationException()
    }

    override fun find(attributes: MutableMap<String, Any>, index: Int): Navigator {
        throw UnsupportedOperationException()
    }

    override fun find(attributes: MutableMap<String, Any>, range: ClosedRange<Int>) {
        throw UnsupportedOperationException()
    }

    override fun find(attributes: MutableMap<String, Any>, selector: String, index: Int) {
        throw UnsupportedOperationException()
    }

    override fun find(attributes: MutableMap<String, Any>, selector: String, range: ClosedRange<Int>) {
        throw UnsupportedOperationException()
    }

}

class SearchContextBasedBasicLocator(val driver: WebDriver, val browserBackedNavigatorFactory: BrowserBackedNavigatorFactory) : BasicLocator {
    override fun find(attributes: MutableMap<String, Any>, selector: String): Navigator {
        return browserBackedNavigatorFactory.createFromWebElements(driver.findElements(By.cssSelector(selector)))!!
    }

    override fun find(bySelector : By) : Navigator {
        val elements = driver.findElements(bySelector)
        return browserBackedNavigatorFactory.createFromWebElements(elements)!!
    }

}