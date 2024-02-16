package controllers

import java.sql._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db._
import play.api.data._
import play.api.data.Forms._
import PersonForm._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(db: Database,cc: MessagesControllerComponents)
 extends MessagesAbstractController(cc) {


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

  def add()=Action{implicit request =>
    Ok(views.html.add("fix form.",form))
  }

  def create()=Action{implicit request =>
    val formdata = form.bindFromRequest()
    val data = formdata.get
    try{
      db.withConnection{conn =>
          val ps=conn.prepareStatement(
            "INSERT INTO people values VALUES(default,?,?,?)"
          )
          ps.setString(1,data.name)
          ps.setString(2,data.email)
          ps.setString(3,data.tel)
          ps.executeUpdate
        }
 
        
      }       catch {
          case e: SQLException => Ok(views.html.add("error",form))
        }
        Redirect(routes.HomeController.index())
  }
}
