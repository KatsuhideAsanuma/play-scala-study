package controllers

import java.sql._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db._
import play.api.data._
import play.api.data.Forms._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(db: Database,cc: MessageControllerComponents)
 extends MessageAbstractController(cc) {


  def index() = Action { implicit request =>
  var msg="database record:<br><ul>"
  try {
    db.withConnection { conn =>
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT * FROM people")
      while (rs.next) {
        msg=msg+"<li>"+rs.getInt("id")+" :"+rs.getString("name")+" "+rs.getString("email")+" "+rs.getString("tel")+"</li>"
      }
      msg=msg+"</ul>"
    }
  } catch {
    case e: SQLException => msg="<li>no record...</li>"
  }
  Ok(views.html.index(msg))
  }
}
