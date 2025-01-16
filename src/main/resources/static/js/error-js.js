$(function() {
    let header = $('.header'); // Знаходимо елемент хедера
    let headerHeight = header.outerHeight(); // Обчислюємо висоту хедера, враховуючи padding та border

    $(window).scroll(function() {
        if ($(this).scrollTop() > 1) { // Якщо прокрутили більше ніж на 1px
            header.addClass('header_fixed'); // Додаємо клас для стилю фіксованого хедера
            $('body').css({
                'paddingTop': headerHeight + 'px' // Додаємо відступ, рівний висоті хедера
            });
        } else { // Якщо повернулися до верхньої частини сторінки
            header.removeClass('header_fixed'); // Видаляємо клас фіксованого хедера
            $('body').css({
                'paddingTop': 0 // Видаляємо відступ
            });
        }
    });
});
