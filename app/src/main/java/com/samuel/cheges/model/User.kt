package com.samuel.cheges.model

class User {


    var name:String=""
    var email:String=""
    var pass:String=""
    var confirmpass:String=""
    var userid:String=""



    constructor(name:String,email:String,pass:String,confirmpass:String,userid:String){



        this.name=name
        this.email=email
        this.pass=pass
        this.confirmpass=confirmpass
        this.userid=userid
    }

    constructor()
}
