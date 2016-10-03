package kebab.locator

import kebab.BrowserBackedNavigatorFactory
import kebab.function.ByFunction
import kebab.navigator.Navigator
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver


/**
 * Created by yy_yank on 2016/10/03.
 */
class SearchContextBasedBasicLocator(val driver: WebDriver, val browserBackedNavigatorFactory: BrowserBackedNavigatorFactory) : BasicLocator {


    val BY_SELECTING_ATTRIBUTES = mapOf<String, ByFunction>(Pair("id", ByFunction({ id ->
        By.id(id)
    })), Pair("clazz", ByFunction({ name ->
        By.className(name)
    })), Pair("name", ByFunction { name ->
        By.name(name)
    }))

    override fun find(attributes: MutableMap<String, Any>, selector: String): Navigator {
        val attributesCopy = attributes
        val selectedUsingBy = findUsingByIfPossible(attributesCopy, selector)
        if (selectedUsingBy != null) {
            return selectedUsingBy
        }
        val optimizedSelector = optimizeSelector(selector, attributesCopy)
        return if (optimizedSelector != null) {
            find(By.cssSelector(optimizedSelector)).filter(attributesCopy)
        } else {
            find(attributes)
        }
    }

    fun find(attributes: MutableMap<String, Any>): Navigator {
        throw UnsupportedOperationException()
    }

    fun findUsingByIfPossible(attributes: MutableMap<String, Any>, selector: String): Navigator? {
        if (attributes.size == 1 && selector == MATCH_ALL_SELECTOR) {
            BY_SELECTING_ATTRIBUTES.asSequence().forEach {
                // TODO hasStringValueForKeyで型安全なんだが as 使うのやだな
                if (hasStringValueForKey(attributes, it.key)) {
                    return find(it.value.invoke(attributes[it.key] as String))
                }
            }
        }
        return null
    }

    fun hasStringValueForKey(attributes: MutableMap<String, Any>, key: String) = attributes.containsKey(key) && attributes[key] is String


    /**
     * Optimizes the selector by translating attributes map into a css attribute selector if possible.
     * Note this method has a side-effect in that it _removes_ those keys from the predicates map.
     */
    fun optimizeSelector(selector: String, attributes: MutableMap<String, Any>): String {
        if (selector == null) {
            return selector
        }

        val buffer = StringBuilder(selector)
        attributes.forEach {
            if (it.key != "text" && it.value is String) {
                if (it.key == "class") {
                    // TODO 正規表現でごにょごにょ
                    //                        it.value.toString().split(Pattern.compile("/\s+/"), 0).forEach { className ->
                    //                            CssSelector.escape(className)
                } else {
                    //                        buffer << """[${attribute.key}="${CssSelector.escape(attribute.value)}"]"""
                }
                attributes.remove(it.key)
            }
        }

        if (buffer.substring(0, 1) == MATCH_ALL_SELECTOR && buffer.length > 1) {
            buffer.deleteCharAt(0)
        }
        return buffer.toString()
    }

    override fun find(bySelector: By): Navigator {
        val elements = driver.findElements(bySelector)
        return browserBackedNavigatorFactory.createFromWebElements(elements)!!
    }
}