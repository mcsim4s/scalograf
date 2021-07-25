package scalograf

import io.circe.{ACursor, Json, JsonObject}

package object model {
  private[scalograf] def encodeAsListObject(field: String)(obj: JsonObject) = {
    obj(field) match {
      case Some(value) => obj.add(field, Json.obj("list" -> value))
      case None        => obj
    }
  }

  private[scalograf] def decodeAsListObject(fieldName: String)(cursor: ACursor) = {
    cursor.withFocus(_.mapObject(obj => {
      obj(fieldName) match {
        case Some(value) =>
          value.asObject.flatMap(_("list")) match {
            case Some(list) => obj.add(fieldName, list)
            case None       => obj
          }
        case None => obj
      }
    }))
  }
}
