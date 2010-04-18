package com.libertech.rtmx.service

import com.libertech.rtmx.model.project.Project
import com.libertech.rtmx.model.common.UserLogin

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
