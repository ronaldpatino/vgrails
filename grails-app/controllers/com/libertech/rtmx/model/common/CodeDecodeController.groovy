package com.libertech.rtmx.model.common

class CodeDecodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [codeDecodeInstanceList: CodeDecode.list(params), codeDecodeInstanceTotal: CodeDecode.count()]
    }

    def create = {
        def codeDecodeInstance = new CodeDecode()
        codeDecodeInstance.properties = params
        return [codeDecodeInstance: codeDecodeInstance]
    }

    def save = {
        def codeDecodeInstance = new CodeDecode(params)
        if (codeDecodeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'codeDecode.label', default: 'CodeDecode'), codeDecodeInstance.id])}"
            redirect(action: "show", id: codeDecodeInstance.id)
        }
        else {
            render(view: "create", model: [codeDecodeInstance: codeDecodeInstance])
        }
    }

    def show = {
        def codeDecodeInstance = CodeDecode.get(params.id)
        if (!codeDecodeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'codeDecode.label', default: 'CodeDecode'), params.id])}"
            redirect(action: "list")
        }
        else {
            [codeDecodeInstance: codeDecodeInstance]
        }
    }

    def edit = {
        def codeDecodeInstance = CodeDecode.get(params.id)
        if (!codeDecodeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'codeDecode.label', default: 'CodeDecode'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [codeDecodeInstance: codeDecodeInstance]
        }
    }

    def update = {
        def codeDecodeInstance = CodeDecode.get(params.id)
        if (codeDecodeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (codeDecodeInstance.version > version) {
                    
                    codeDecodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'codeDecode.label', default: 'CodeDecode')] as Object[], "Another user has updated this CodeDecode while you were editing")
                    render(view: "edit", model: [codeDecodeInstance: codeDecodeInstance])
                    return
                }
            }
            codeDecodeInstance.properties = params
            if (!codeDecodeInstance.hasErrors() && codeDecodeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'codeDecode.label', default: 'CodeDecode'), codeDecodeInstance.id])}"
                redirect(action: "show", id: codeDecodeInstance.id)
            }
            else {
                render(view: "edit", model: [codeDecodeInstance: codeDecodeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'codeDecode.label', default: 'CodeDecode'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def codeDecodeInstance = CodeDecode.get(params.id)
        if (codeDecodeInstance) {
            try {
                codeDecodeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'codeDecode.label', default: 'CodeDecode'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'codeDecode.label', default: 'CodeDecode'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'codeDecode.label', default: 'CodeDecode'), params.id])}"
            redirect(action: "list")
        }
    }
}
