package com.example.book.model;

import org.hibernate.annotations.GenericGenerator;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;

import javax.persistence.*;
import java.util.Set;

/**
 * @author cty
 * @date 2022/6/22
 */
@Erupt(name = "UserBooks", power = @Power(importable = true, export = true))
@Table(name = "user_books")
@Entity
public class UserBooks {
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "native")
    @Column(name = "id")
    @EruptField
    private Long id;

    @EruptField(
            views = @View(title = "用户ID"),
            edit = @Edit(title = "用户ID")
    )
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_book_id")
    @OrderBy
    @EruptField(
            edit = @Edit(title = "用户的书", type = EditType.TAB_TABLE_ADD),
            views = @View(title = "用户的书")
    )
    private Set<CartDetails> userBookDetails;

    @EruptField(
            views = @View(title = "总价", sortable = true),
            edit = @Edit(title = "总价", search = @Search(vague = true), notNull = true)
    )
    private Float countPrice;
}
