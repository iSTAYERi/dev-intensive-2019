package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    private var validation = Validation.OK

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        validationCheck(answer)
        return if (question.answer.contains(answer) || question == Question.IDLE) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else if (validation != Validation.OK) {
            "${validation.msg}\n${question.question}" to status.color
        } else if (status == Status.CRITICAL) {
            status = status.nextStatus()
            question = Question.NAME
            "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
        } else {
            status = status.nextStatus()
            "Это неправильный ответ\n${question.question}" to status.color
        }
    }

    private fun validationCheck(data: String) {
        validation = when (question) {
            Question.NAME -> {
                when {
                    data.isBlank() -> Validation.ERROR_NAME
                    data[0].isUpperCase() -> Validation.OK
                    else -> Validation.ERROR_NAME
                }
            }
            Question.PROFESSION -> {
                when {
                    data.isBlank() -> Validation.ERROR_PROFESSION
                    data[0].isLowerCase() -> Validation.OK
                    else -> Validation.ERROR_PROFESSION
                }
            }
            Question.MATERIAL -> {
                when {
                    data.isBlank() -> Validation.ERROR_MATERIAL
                    data.contains(Regex("\\d")) -> Validation.ERROR_MATERIAL
                    else -> Validation.OK
                }
            }
            Question.BDAY -> {
                when {
                    data.isBlank() -> Validation.ERROR_BDAY
                    data.isDigitsOnly() -> Validation.OK
                    else -> Validation.ERROR_BDAY
                }
            }
            Question.SERIAL -> {
                when {
                    data.isBlank() -> Validation.ERROR_SERIAL
                    data.isDigitsOnly() && data.length == 7 -> Validation.OK
                    else -> Validation.ERROR_SERIAL
                }
            }
            Question.IDLE -> {
                Validation.OK
            }
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answer: List<String>) {
        NAME("Как меня зовут?", listOf("Бендер", "Bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }

    enum class Validation(val msg: String) {
        OK(""),
        ERROR_NAME("Имя должно начинаться с заглавной буквы"),
        ERROR_PROFESSION("Профессия должна начинаться со строчной буквы"),
        ERROR_MATERIAL("Материал не должен содержать цифр"),
        ERROR_BDAY("Год моего рождения должен содержать только цифры"),
        ERROR_SERIAL("Серийный номер содержит только цифры, и их 7")
    }

}