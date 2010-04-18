import com.libertech.rtmx.model.common.UserLogin

class BootStrap {

     def init = { servletContext ->
      // is also executed when running tests -> provide empty or dummy initial data file
        // within the project's root path
       // DbUnitOperator.create()
     }
     def destroy = {
     }
} 