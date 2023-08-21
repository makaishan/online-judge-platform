package com.mks.luooj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mks.luooj.common.BaseResponse;
import com.mks.luooj.common.ErrorCode;
import com.mks.luooj.common.ResultUtils;
import com.mks.luooj.exception.BusinessException;
import com.mks.luooj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.mks.luooj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.mks.luooj.model.entity.QuestionSubmit;
import com.mks.luooj.model.entity.User;
import com.mks.luooj.model.vo.QuestionSubmitVO;
import com.mks.luooj.service.QuestionSubmitService;
import com.mks.luooj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest questionSubmitAddRequest
     * @param request request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取题目提交列表（仅管理员可查看全部信息，普通用户只能查看非代码、答案等公开信息）
     *
     * @param questionSubmitQueryRequest questionQueryRequest
     * @return BaseResponse<Page<Question>>
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        //根据权限脱敏
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }

}
