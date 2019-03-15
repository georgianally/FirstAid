package com.example.firstaid.model;

import com.example.firstaid.R;

public class Protocol {
    private Page[] pages;

    public Protocol() {
        pages = new Page[11];

        pages[0] = new Page(R.string.page0,
                new Choice(R.string.page0_choice1, 2),
                new Choice(R.string.page0_choice2, 1));

        pages[1] = new Page(R.string.page1,
                new Choice(R.string.page1_choice1, 2),
                new Choice(R.string.page1_choice2, 3));

        pages[2] = new Page(R.string.page2,
                new Choice(R.string.page2_choice1, 3),
                new Choice(R.string.page2_choice2, 3));

        pages[3] = new Page(R.string.page3,
                new Choice(R.string.page3_choice1, 4),
                new Choice(R.string.page3_choice2, 5));

        pages[4] = new Page(R.string.page4);

        pages[5] = new Page(R.string.page5);

        pages[6] = new Page(R.string.page6);

        pages[7] = new Page(R.string.page7,
                new Choice(R.string.done_button_text, 8));

        pages[8] = new Page(R.string.page8);

        pages[9] = new Page(R.string.page9,
                new Choice(R.string.page9_choice1, 8),
                new Choice(R.string.page9_choice2, 8));
    }

    public Page getPage(int pageNumber) {
        //out of bounds error catch
        if (pageNumber >= pages.length) {
            pageNumber = 0;
        }
        return pages[pageNumber];
    }
}
