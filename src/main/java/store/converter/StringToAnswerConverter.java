package store.converter;

import store.domain.Answer;

public class StringToAnswerConverter implements Converter<String, Answer> {
    @Override
    public Answer convert(String source) {
        validate(source);
        String trimSource = source.trim();
        if (trimSource.equals("Y")) {
            return Answer.YES;
        }
        if (trimSource.equals("N")) {
            return Answer.NO;
        }

        throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다.");
    }

    private void validate(String source) {
        if (source == null || source.isEmpty()) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다.");
        }
    }
}
