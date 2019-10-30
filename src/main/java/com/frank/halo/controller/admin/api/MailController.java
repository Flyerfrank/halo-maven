package com.frank.halo.controller.admin.api;

import com.frank.halo.model.params.MailParam;
import com.frank.halo.model.support.BaseResponse;
import com.frank.halo.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Mail controller.
 *
 * @author johnniang
 * @date 19-5-7
 */
@RestController
@RequestMapping("/api/admin/mails")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("test")
    public BaseResponse testMail(@Valid @RequestBody MailParam mailParam) {
        mailService.sendMail(mailParam.getTo(), mailParam.getSubject(), mailParam.getContent());
        return BaseResponse.ok("发送成功");
    }
}
