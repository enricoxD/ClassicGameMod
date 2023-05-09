/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 *
 * Copyright (c) 2016 - 2023 CCBlueX
 *
 * LiquidBounce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LiquidBounce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LiquidBounce. If not, see <https://www.gnu.org/licenses/>.
 */

package de.miraculixx.cgames.ultralight.js.bindings

import com.labymedia.ultralight.databind.context.ContextProvider
import com.labymedia.ultralight.javascript.JavascriptObject
import com.labymedia.ultralight.javascript.JavascriptPropertyAttributes
import de.miraculixx.cgames.ultralight.ViewOverlay
import de.miraculixx.cgames.utils.logger

/**
 * Referenced by JS as `events`
 */
class UltralightJsEvents(private val viewContextProvider: ContextProvider, val viewOverlay: ViewOverlay) {

    companion object {

        /**
         * Contains mappings from event names to the corresponding classes
         */
        private val EVENT_MAP = mutableMapOf<String, () -> Unit>()

        init {

            // Register view related events
//            EVENT_MAP["close"] = ViewCloseEvent::class.java
        }

    }

    /**
     * Contains all events that are registered in the current context
     */
    private val _registeredEvents = mutableMapOf<() -> Unit, List<String>>()

    fun on(name: String, handler: JavascriptObject) {
        if (!handler.isFunction) {
            throw IllegalArgumentException("$handler is not a function.")
        }

        // Do we know an event that has this name? What is the event class behind this name?
        val eventClass = EVENT_MAP[name] ?: throw IllegalArgumentException("Unknown event: $name")

        // Get the list of the current event type
        val hookList = _registeredEvents.computeIfAbsent(eventClass) { ArrayList() }

        // The event function is stored in this property
        val propertyName = "engine__${name}__${hookList.size}"

        // Make a property with the name engine__ plus the name of the event. This is made to
        // make the function accessible for the event handler
        viewContextProvider.syncWithJavascript {
            it.context.globalObject.setProperty(propertyName, handler, JavascriptPropertyAttributes.NONE)
        }

        // Create an event hook that will call the JS function
//        val eventHook = EventHook<Event>(
//            this,
//            { event ->
//                val callEvent = {
//                    runCatching {
//                        viewContextProvider.syncWithJavascript {
//                            it.context.globalObject.getProperty(propertyName).toObject().callAsFunction(
//                                it.context.globalObject,
//                                viewOverlay.context.databind.conversionUtils.toJavascript(it.context, event)
//                            )
//                        }
//                    }.onFailure {
//                        logger.error("Ultralight JS Engine", it)
//                    }
//                }
//
//                if (RenderSystem.isOnRenderThread()) {
//                    callEvent()
//                    return@EventHook
//                }
//
//                RenderSystem.recordRenderCall {
//                    callEvent()
//                }
//            },
//            false
//        )

        // Add the event hook to the list
//        hookList.add(eventHook)

        // Register the event hook
//        EventManager.registerEventHook(eventClass, eventHook)
    }

    /**
     * Unregisters all events that are registered by this wrapper
     */
//    fun _unregisterEvents() {
//        for ((clazz, hooks) in this._registeredEvents) {
//            for (hook in hooks) {
//                EventManager.unregisterEventHook(clazz, hook)
//            }
//        }
//
//        this._registeredEvents.clear()
//    }

    /**
     * Directly call event to ultralight view
     */
//    fun _directlyCallEvent(event: Event) {
//        val target = _registeredEvents[event.javaClass] ?: return
//
//        for (eventHook in target) {
//            runCatching {
//                eventHook.handler(event)
//            }.onFailure {
//                logger.error("Exception while executing handler.")
//            }
//        }
//    }

//    fun _fireViewClose(): Boolean {
//        val viewCloseEvent = ViewCloseEvent()
//        _directlyCallEvent(viewCloseEvent)
//        return !viewCloseEvent.isCancelled
//    }
}
