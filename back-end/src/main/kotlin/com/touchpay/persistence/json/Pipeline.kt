package com.touchpay.persistence.json

class Pipeline() : JsonArrayBuilder() {
    constructor(init: Pipeline.() -> Unit) : this() {
        init()
    }

    private fun privateReplaceRoot(newRoot: Any) {
        add(JsonBuilder {
            "\$replaceRoot" to {
                "newRoot" to newRoot
            }
        })
    }

    fun replaceRoot(newRoot: String) {
        privateReplaceRoot(newRoot)
    }

    fun replaceRoot(newRoot: JsonBuilder.() -> Unit) {
        val jsonBuilder = JsonBuilder()
        jsonBuilder.newRoot()
        privateReplaceRoot(jsonBuilder.json)
    }

    fun unwind(path: String, includeArrayIndex: String? = null, preserveNullAndEmptyArrays: Boolean? = null) {
        add(JsonBuilder {
            "\$unwind" to {
                "path" to path

                if (!includeArrayIndex.isNullOrBlank()) {
                    "includeArrayIndex" to includeArrayIndex
                }

                if (preserveNullAndEmptyArrays != null) {
                    "preserveNullAndEmptyArrays" to preserveNullAndEmptyArrays
                }
            }
        })
    }

    infix fun addField(init: AddField.() -> Unit){
        val addFiled = AddField()
        addFiled.init()
        add(JsonBuilder { "\$addFields" to addFiled.json})
    }

    infix fun match(init: Match.() -> Unit) {
        val match = Match()
        match.init()
        add(JsonBuilder { "\$match" to match.json })
    }

    infix fun group(init: Group.() -> Unit) {
        val group = Group()
        group.init()
        add(JsonBuilder { "\$group" to group.json })
    }

    infix fun projection(init: Projection.() -> Unit) {
        val projection = Projection()
        projection.init()
        add(JsonBuilder { "\$project" to projection.json })
    }

    private fun privateLookup(from: String, alias: String, body: JsonBuilder) {
        body.apply {
            "from" to from
            "as" to alias
        }

        add(JsonBuilder {
            "\$lookup" to body.json
        })
    }

    fun lookup(from: String, alias: String, localField: String, foreignField: String) {
        privateLookup(from, alias, JsonBuilder {
            "localField" to localField
            "foreignField" to foreignField
        })
    }

    fun lookup(from: String, alias: String, pipeline: Pipeline, let: JsonBuilder? = null) {
        privateLookup(from, alias, JsonBuilder {
            "pipeline" to pipeline.json
            if (let != null) {
                "let" to let.json
            }
        })
    }

    fun limit(value: Int) {
        add(JsonBuilder { "\$limit" to value})
    }

    fun skip(value: Int) {
        add(JsonBuilder { "\$skip" to value})
    }

    fun sort(init: Sort.() -> Unit) {
        val sort = Sort()
        sort.init()
        add(JsonBuilder { "\$sort" to sort.json })
    }
}
