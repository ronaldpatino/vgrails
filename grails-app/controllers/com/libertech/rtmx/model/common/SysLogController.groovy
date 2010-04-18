package com.libertech.rtmx.model.common

class SysLogController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [sysLogInstanceList: SysLog.list(params), sysLogInstanceTotal: SysLog.count()]
    }

    def create = {
        def sysLogInstance = new SysLog()
        sysLogInstance.properties = params
        return [sysLogInstance: sysLogInstance]
    }

    def save = {
        def sysLogInstance = new SysLog(params)
        if (sysLogInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'sysLog.label', default: 'SysLog'), sysLogInstance.id])}"
            redirect(action: "show", id: sysLogInstance.id)
        }
        else {
            render(view: "create", model: [sysLogInstance: sysLogInstance])
        }
    }

    def show = {
        def sysLogInstance = SysLog.get(params.id)
        if (!sysLogInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sysLog.label', default: 'SysLog'), params.id])}"
            redirect(action: "list")
        }
        else {
            [sysLogInstance: sysLogInstance]
        }
    }

    def edit = {
        def sysLogInstance = SysLog.get(params.id)
        if (!sysLogInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sysLog.label', default: 'SysLog'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [sysLogInstance: sysLogInstance]
        }
    }

    def update = {
        def sysLogInstance = SysLog.get(params.id)
        if (sysLogInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (sysLogInstance.version > version) {
                    
                    sysLogInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'sysLog.label', default: 'SysLog')] as Object[], "Another user has updated this SysLog while you were editing")
                    render(view: "edit", model: [sysLogInstance: sysLogInstance])
                    return
                }
            }
            sysLogInstance.properties = params
            if (!sysLogInstance.hasErrors() && sysLogInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'sysLog.label', default: 'SysLog'), sysLogInstance.id])}"
                redirect(action: "show", id: sysLogInstance.id)
            }
            else {
                render(view: "edit", model: [sysLogInstance: sysLogInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sysLog.label', default: 'SysLog'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def sysLogInstance = SysLog.get(params.id)
        if (sysLogInstance) {
            try {
                sysLogInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'sysLog.label', default: 'SysLog'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'sysLog.label', default: 'SysLog'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sysLog.label', default: 'SysLog'), params.id])}"
            redirect(action: "list")
        }
    }
}
