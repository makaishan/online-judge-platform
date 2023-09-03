package com.mks.luoojbackendquestionservice.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mks.luoojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.mks.luoojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.mks.luoojbackendmodel.model.entity.QuestionSubmit;
import com.mks.luoojbackendmodel.model.entity.User;
import com.mks.luoojbackendmodel.model.vo.QuestionSubmitVO;

/**
* @author lenovo-mr
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-08-20 16:55:13
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest questionId
     * @param loginUser loginUser
     * @return int
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest questionQueryRequest
     * @return QueryWrapper<QuestionSubmit>
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目提交封装
     *
     * @param questionSubmit questionSubmit
     * @param loginUser loginUser
     * @return QuestionSubmitVO
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目提交封装
     *
     * @param questionSubmitPage questionSubmitPage
     * @param loginUser loginUser
     * @return Page<QuestionSubmitVO>
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);

}
