package me.nikhilchaudhari.usehttp.helper

import java.io.File
import java.io.Writer
import java.nio.file.Path

internal fun Writer.writeAndFlush(string: String) {
    this.write(string)
    this.flush()
}

fun ByteArray.splitLines(): List<ByteArray> {
    if (this.isEmpty()) return listOf()
    val lines = arrayListOf<ByteArray>()
    var lastSplit = 0
    var skip = 0
    for ((i, byte) in this.withIndex()) {
        if (skip > 0) {
            skip--
            continue
        }
        if (byte == '\n'.toByte()) {
            lines.add(this.sliceArray(lastSplit until i))
            lastSplit = i + 1
        } else if (byte == '\r'.toByte() && i + 1 < this.size && this[i + 1] == '\n'.toByte()) {
            skip = 1
            lines.add(this.sliceArray(lastSplit until i))
            lastSplit = i + 2
        } else if (byte == '\r'.toByte()) {
            lines.add(this.sliceArray(lastSplit until i))
            lastSplit = i + 1
        }
    }
    lines += this.sliceArray(lastSplit until this.size)
    return lines
}

fun ByteArray.split(delimiter: ByteArray): List<ByteArray> {
    val lines = arrayListOf<ByteArray>()
    var lastSplit = 0
    var skip = 0
    for (i in this.indices) {
        if (skip > 0) {
            skip--
            continue
        }
        if (this.sliceArray(i until i + delimiter.size).toList() == delimiter.toList()) {
            skip = delimiter.size
            lines += this.sliceArray(lastSplit until i)
            lastSplit = i + delimiter.size
        }
    }
    lines += this.sliceArray(lastSplit until this.size)
    return lines
}

internal fun <T> Class<T>.getSuperclasses(): List<Class<in T>> {
    val list = arrayListOf<Class<in T>>()
    var superclass = this.superclass
    while (superclass != null) {
        list.add(superclass)
        superclass = superclass.superclass
    }
    return list
}

fun <K, V> MutableMap<K, V>.putIfAbsentWithNull(key: K, value: V) {
    if (key !in this) {
        this[key] = value
    }
}

fun <K, V> MutableMap<K, V>.putAllIfAbsentWithNull(other: Map<K, V>) {
    for ((key, value) in other) {
        this.putIfAbsentWithNull(key, value)
    }
}

fun File.asFile(name: String = this.name) = RawFiles(name, this)

fun Path.asFile() = RawFiles(this)

fun Path.asFile(name: String) = RawFiles(name, this)

fun String.asFile(name: String) = RawFiles(name, this)
