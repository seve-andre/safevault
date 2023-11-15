package com.mitch.safevault.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * [ProtoUserFlowSerializer] is used to serialize/deserialize from Proto Datastore,
 * in particular the [ProtoUserPreferences] datastore.
 *
 * see more at [Proto Datastore](https://developer.android.com/topic/libraries/architecture/datastore#proto-create)
 */
class ProtoUserFlowSerializer @Inject constructor() : Serializer<ProtoUserFlow> {
    override val defaultValue: ProtoUserFlow
        get() = ProtoUserFlow.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoUserFlow =
        try {
            ProtoUserFlow.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: ProtoUserFlow, output: OutputStream) = t.writeTo(output)
}
