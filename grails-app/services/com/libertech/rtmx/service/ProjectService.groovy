package com.libertech.rtmx.service

import org.zhakimel.vgrails.model.core.UserLogin
import com.libertech.erp.model.project.Project

/**
 * Provides Project Management DAO and Business Logic
 * @author Abiel Hakeem
 */
class ProjectService {

    boolean transactional = true

    LoggerService loggerService = new LoggerService()

      UserLogin userLogin;

    def findAllProject() {

      def lst = Project.findAll()
      return lst

    }

    def getProject(int id){

      def project = Project.get(id)
      return project

    }

    def saveProject(Project project){
      project.save()
    }


    def deleteProject(int id){
     def project = Project.get(id)
     project.delete()
    }

}
