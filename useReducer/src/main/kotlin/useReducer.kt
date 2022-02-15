/**
 * This is a builder for the actions
 * This constructs an actionName -> action map with the [does] function
 * You have to use `useReducer{...}` block.
 * @see [useReducer]
 */
class ActionBuilder(private val actionsMap: MutableMap<String, (() -> Unit)?>) {

    private var actionName: String = ""
    private var action: (() -> Unit)? = null

    /**
     * Registers a single action
     */
    infix fun String.does(action: () -> Unit) {
        actionsMap[this] = action
    }
}

/**
 * Dispatcher is used dispatch the actions in order to execute them
 * <pre><code>
 * val dispatcher = userReducer {...}
 * dispatcher.dispatch("actionName")
 * </code></pre>
 * @see [useReducer] for more usage
 */
class Dispatcher(private val actionsMap: MutableMap<String, (() -> Unit)?>) {

    /**
     * Dispatches/executes the action specified with the provided [actionName]
     *
     * @param [actionName] String name of your action defined in [useReducer]
     */
    fun dispatch(actionName: String){
        val action = actionsMap[actionName]
        action?.invoke()
    }
}

/**
 * useReducer{..} hook is used to register multiple actions to update state at one place
 * (If you are familiar with Redux, you already know how it works.)
 *
 * [useReducer] is preferable when you have a complex state logic involved in the code
 * and involves multiple sub-values or when the next state depends on the previous one.
 *
 * You can define the initialState as you generally define with `remember { mutableStateOf(..) }`
 * and can specify the changes for that state with the particular action name.
 * For example you want to increment the button click count -
 * `"increment" does { count++ }`
 * This registers one action in the [useReducer]
 *
 * For example (in detail):
 * <pre>
 * <code>
 * val count by remember { mutableStateOf(0) }
 * val dispatcher = useReducer {
 *  "increment" does { count++ }
 *  "decrement" does { count-- }
 * }
 *
 * Button(onClick =  { dispatcher.dispatch("increment") }){...}
 * </code></pre>
 */
fun useReducer(actions: ActionBuilder.() -> Unit): Dispatcher{
    val actionsMap = mutableMapOf<String, (() -> Unit)?>()
    ActionBuilder(actionsMap).apply(actions)
    return Dispatcher(actionsMap)
}
