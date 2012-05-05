package code.snippet

import code.model.User
import code.model.Account

import scala.xml.NodeSeq
import net.liftweb.util.Helpers
import net.liftweb.util.Helpers._
import net.liftweb.util.CssSel
import net.liftweb.http.S

class Login {

  def loggedIn = {
    if(!User.loggedIn_?){
      "*" #> NodeSeq.Empty
    }else{
      ".logout [href]" #> {
        User.logoutPath.foldLeft("")(_ + "/" + _)
      } &
      ".username *" #> User.currentUser.get.email.get &
      ".account-number *" #> Account.currentAccount.get.number.get
    }
  }
  
  def loggedOut = {
    if(User.loggedIn_?){
      "*" #> NodeSeq.Empty
    } else {
      ".login [action]" #> User.loginPageURL &
      ".forgot [href]" #> {
        val href = for {
          menu <- User.resetPasswordMenuLoc
        } yield menu.loc.calcDefaultHref
        href getOrElse "#"
      } & {
        ".signup [href]" #> {
         User.signUpPath.foldLeft("")(_ + "/" + _)
        }
      }
    }
  }

  def redirectTesobeAnonymousIfLoggedOut = {
    S.redirectTo("/accounts/tesobe/anonymous")
  }

}