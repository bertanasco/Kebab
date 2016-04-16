/**
 * Created by yy_yank on 2015/12/19.
 */
package kebab

import java.util.*


class PageContentTemplateBuilder(val browser: Browser, val container: PageContentContainer, val navigatorFactory: NavigatorFactory) {
    val templates = HashMap<String, PageContentTemplate>()

    companion object {

        fun build(browser: Browser,
                  container: PageContentContainer,
                  navigatorFactory: NavigatorFactory,
                  property: String, startAt: Class<*>,
                  stopAt: Class<*> = Any::class.java): HashMap<String, PageContentTemplate> {

            if (!stopAt.isAssignableFrom(startAt)) {
                throw IllegalArgumentException("$startAt is not a subclass of $stopAt")
            }

            val templatesDefinitions = listOf("1")
            var clazz = startAt

            while (clazz != stopAt) {
                //                        var templatesDefinition =
                //noinspection GroovyUnusedCatchParameter
                //                                try {
                //                                    clazz[property]
                //                                } catch (MissingPropertyException e) {
                //                                    // swallow
                //                                }
                //
                //                        if (templatesDefinition) {
                //                            if (!(templatesDefinition is Closure)) {
                //                                throw IllegalArgumentException("'$property' static property of class $clazz should be a Closure")
                //                            }
                //                            templatesDefinitions << templatesDefinition.clone()
                //                        }
                //
                clazz = clazz.superclass
            }
            return build(browser, container, navigatorFactory, templatesDefinitions)
        }

        fun build(browser: Browser,
                  container: PageContentContainer,
                  navigatorFactory: NavigatorFactory,
                  templatesDefinitions: List<Any>): HashMap<String, PageContentTemplate> {
            val builder = PageContentTemplateBuilder(browser, container, navigatorFactory)
            //        for (templatesDefinition in templatesDefinitions) {
            //            templatesDefinition.delegate = builder
            //            templatesDefinition()
            //        }
            return builder.templates
        }
    }
}

// TODO 作成中
class PageContentTemplate {
    lateinit var browser: Browser
    lateinit var owner: PageContentContainer
    lateinit var name: String
    lateinit var params: PageContentTemplateParams
    lateinit var factory: Function<Any>
    lateinit var navigatorFactory: NavigatorFactory
    val cache = emptyMap<String, Any>()

    constructor(browser: Browser, owner: PageContentContainer, name: String, params: Map<String, Any>, factory: Function<Any>, navigatorFactory: NavigatorFactory) {
        this.browser = browser
        this.owner = owner
        this.name = name
        //        this.params = PageContentTemplateParams(this, params)
        this.factory = factory
        this.navigatorFactory = navigatorFactory
    }

    override fun toString() = "content template '$name' defined by $owner"


    fun getConfig() = browser.config


    //    fun get(args : Any) = params.cache ? fromCache(*args) : create(*args)


    private fun create(args: Array<Any>) {
        val createAction = {
            //            val factoryReturn = invokeFactory( * args)
            //            val creation = wrapFactoryReturn(factoryReturn, *args)
            //            if (params.required) {
            //                if (creation is TemplateDerivedPageContent) {
            //                    creation.require()
            //                } else if (creation == null) {
            //                    throw RequiredPageValueNotPresent(this, *args)
            //                }
            //            }
            //            creation
        }

        //        val wait = config.getWaitForParam(params.wait)
        //        if (wait) {
        //            try {
        //                wait.waitFor(createAction)
        //            } catch (WaitTimeoutException e) {
        //                if (params.required) {
        //                    throw e
        //                }
        //                e.lastEvaluationValue
        //            }
        //        } else {
        //            createAction()
        //        }
    }

    private fun fromCache(args: Array<Any>) {
        val argsHash = Arrays.deepHashCode(args)
        //        if (!cache.containsKey(argsHash)) {
        //            cache[argsHash] = create(*args)
        //        }
        //        return cache[argsHash]
    }

    //    private fun invokeFactory(args : Array<Any>) {
    //        factory.delegate = createFactoryDelegate(args)
    //        factory.resolveStrategy = Closure.DELEGATE_FIRST
    //        factory(*args)
    //    }

    private fun createFactoryDelegate(args: Array<Any>) {
        PageContentTemplateFactoryDelegate(this, args)
    }

    private fun <T> wrapFactoryReturn(factoryReturn: T, args: Array<Any>) {
        //        if (factoryReturn is Module) {
        //            factoryReturn.init(this, args)
        //        }
        //        if (factoryReturn is Navigator) {
        //            return TemplateDerivedPageContent(browser, this, factoryReturn, *args)
        //        } else {
        //            return factoryReturn
        //        }
        //    }
    }

    class TemplateDerivedPageContent {

    }

    class PageContentTemplateFactoryDelegate(pageContentTemplate: PageContentTemplate, args: Array<Any>) {

    }

    class PageContentTemplateParams(val template: PageContentTemplate, val params: PageContentTemplateParams) {}
}