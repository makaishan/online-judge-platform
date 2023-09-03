package com.mks.luoojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mks.luoojbackendmodel.model.dto.question.QuestionQueryRequest;
import com.mks.luoojbackendmodel.model.entity.Question;
import com.mks.luoojbackendmodel.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author lenovo-mr
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-08-20 16:55:13
*/
public interface QuestionService extends IService<Question> {

    /**
     * 校验
     *
     * @param question question
     * @param add add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest questionQueryRequest
     * @return QueryWrapper<Question>
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);


    /**
     * 获取帖子封装
     *
     * @param question question
     * @param request request
     * @return QuestionVO
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param questionPage questionPage
     * @param request request
     * @return Page<QuestionVO>
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

}
