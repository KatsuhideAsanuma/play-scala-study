package controllers



object PersonForm {
import play.api.data._
import play.api.data.Forms._

case class Data(name: String, email: String, tel: String)

  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> email,
      "tel" -> text
    )(Data.apply)(Data.unapply)
  )
}