package store.domain.discount;

import store.domain.Answer;

public class AnswerDiscountCondition implements DiscountCondition {
    private final Answer answer;

    public AnswerDiscountCondition(Answer answer) {
        this.answer = answer;
    }

    @Override
    public boolean isSatisfiedBy() {
        return answer.toBoolean();
    }
}
