package scalograf
package model

import model.Template.Current

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Template(
    `type`: String, //ToDo enum
    allValue: Option[String] = None, //ToDo what is this?
    current: Current = Current(),
    datasource: Option[String] = None,
    definition: String = "",
    description: Option[String] = None,
    error: Option[Template.Error] = None,
    hide: Option[Int] = None,
    includeAll: Boolean = false,
    label: String = "",
    multi: Boolean = false,
    name: String = "",
    options: List[String] = List.empty,
    query: String = "",
    queryValue: String = "",
    refresh: Int = 0, //ToDo enum ???
    regex: String = "",
    skipUrlSync: Boolean = false,
    sort: Int = 0, //ToDo enum
    tagValuesQuery: String = "",
    tagsQuery: String = "",
    tags: List[String] = List.empty,
    useTags: Boolean = false
)

object Template {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val errorCodec = deriveConfiguredCodec[Error]
  implicit val currentCodec = deriveConfiguredCodec[Current]
  implicit val codec = deriveConfiguredCodec[Template]

  case class Current(selected: Boolean = false, text: String = "", value: String = "", tags: List[String] = List.empty)
  case class Error()
}
