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

def create() = Action { implicit request =>
  form.bindFromRequest.fold(
    formWithErrors => {
      // バインド失敗時の処理
      BadRequest(views.html.add("エラーが発生しました。", formWithErrors))
    },
    formData => {
      // バインド成功時の処理
      try {
        db.withConnection { conn =>
          // データをデータベースに挿入
          val ps = conn.prepareStatement("INSERT INTO people VALUES (default, ?, ?, ?)")
          ps.setString(1, formData.name)
          ps.setString(2, formData.email)
          ps.setString(3, formData.tel)
          ps.executeUpdate()

          // 挿入後のデータベースの状態を取得して出力
          val stmt = conn.createStatement()
          val rs = stmt.executeQuery("SELECT * FROM people")
          println("データベースの現在の状態:")
          while (rs.next()) {
            println(s"ID: ${rs.getInt("id")}, 名前: ${rs.getString("name")}, メール: ${rs.getString("email")}, 電話番号: ${rs.getString("tel")}")
          }
        }
        Redirect(routes.HomeController.index())
      } catch {
        case e: SQLException => BadRequest(views.html.add("データベースエラーが発生しました。", form))
      }
    }
  )
}
}
