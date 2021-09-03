insert into users
values ('admin', '123456', 'F', 1, 0, 1, 'admin', default, './icon.png'),
       ('zerorains', '123456', 'M', 0, 1, 0, 'zerorains', default, './icon.png');

insert into dynamic
values (default, '美食', '#休闲时光#', 1, 0, '这是新世界的开始', '不管别人怎么说，珂朵莉是世界上第一幸福的女孩。', '2020-9-3', 'admin'),
       (default, '运动', '#休闲时光#', 0, 0, '三句话，让他跑地球三圈', '我是一个善于让人跑步的精通人性的讲师。', '2020-9-3', 'zerorains');

insert into remark
values (default, '2020-9-3', 60, '我是世界第一珂学家！', 'zerorains', 1),
       (default, '2020-9-3', 0, '珂朵莉永远活在我心里！', 'admin', 1);

insert into photo
values ('./icon.png', 1),
       ('./nmsl.jpg', 2);

insert into favor
values ('zerorains', 1, 'T', 'F');

insert into concern
values ('zerorains', 'admin');

insert into thumbsup
values ('zerorains', 1, 'T')