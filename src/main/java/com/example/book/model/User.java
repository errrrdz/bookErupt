package com.example.book.model;

import org.hibernate.annotations.GenericGenerator;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.toolkit.handler.SqlChoiceFetchHandler;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "user")
@Erupt(name = "User", power = @Power(importable = true, export = true))
@Entity
public class User {
    @Id
    @Column(name = "id")
    @EruptField(
            views = @View(title = "用户账号"),
            edit = @Edit(title = "用户账号", search = @Search)
    )
    private Long id;

    @EruptField(
            views = @View(title = "真实姓名"),
            edit = @Edit(title = "真实姓名", notNull = true, inputType = @InputType, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            edit = @Edit(title = "密码输入", inputType = @InputType(type = "password"))
    )
    private String password;

    @EruptField(
            views = @View(title = "头像"),
            edit = @Edit(title = "头像", type = EditType.ATTACHMENT,
                    attachmentType = @AttachmentType(type = AttachmentType.Type.IMAGE, maxLimit = 1))
    )
    private String picture;

    @EruptField(
            views = @View(title = "性别"),
            edit = @Edit(title = "性别", type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "保密", value = "0"),
                                    @VL(label = "男", value = "1"),
                                    @VL(label = "女", value = "2"),
                            }
                    ))
    )
    private int gender = 0;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "正常", value = "0"),
                                    @VL(label = "冻结", value = "1"),
                            }
                    ))
    )
    private int status = 0;

    @EruptField(
            views = @View(title = "是否注销"),
            edit = @Edit(title = "是否注销", type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "未注销", value = "0"),
                                    @VL(label = "已注销", value = "1"),
                            }
                    ))
    )
    private int delFlag = 0;

    @EruptField(
            views = @View(title = "角色"),
            edit = @Edit(
                    notNull = true,
                    search = @Search,
                    title = "角色",
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            fetchHandler = SqlChoiceFetchHandler.class,
                            //参数一必填，表示sql语句
                            //参数二可不填，表示缓存时间，默认为3000毫秒，1.6.10及以上版本支持
                            fetchHandlerParams = {"select id, ch_name from role", "5000"}
                    ))
    )
    private Long role;

    @EruptField(
            views = @View(title = "生日"),
            edit = @Edit(title = "生日", type = EditType.DATE, dateType = @DateType(pickerMode = DateType.PickerMode.HISTORY))
    )
    private String birthday;

    @Lob
    @EruptField(
            edit = @Edit(title = "用户简介", type = EditType.TEXTAREA)
    )
    private String des;

    @EruptField(
            views = @View(title = "openid")
    )
    private String openid;

    @EruptField(
            views = @View(title = "邮箱"),
            edit = @Edit(title = "邮箱", notNull = true, inputType = @InputType, search = @Search(vague = true))
    )
    private String email;

    @EruptField(
            views = @View(title = "盐")
    )
    private String salt;

    @EruptField(
            views = @View(title = "用户名"),
            edit = @Edit(title = "用户名", notNull = true, inputType = @InputType, search = @Search(vague = true))
    )
    private String username;

    @EruptField(
            views = @View(title = "创建时间"),
            edit = @Edit(title = "创建时间", type = EditType.DATE, dateType = @DateType(pickerMode = DateType.PickerMode.HISTORY))
    )
    private Date createTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @OrderBy
    @EruptField(
            edit = @Edit(title = "地址列表", type = EditType.TAB_TABLE_ADD),
            views = @View(title = "地址列表")
    )
    private Set<Address> addressList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @OrderBy
    @EruptField(
            edit = @Edit(title = "词云列表", type = EditType.TAB_TABLE_ADD),
            views = @View(title = "词云列表")
    )
    private Set<UserWord> wordList;
}
