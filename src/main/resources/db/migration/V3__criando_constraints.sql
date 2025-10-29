
    alter table if exists discente 
       add constraint FKmh2s5m3ikablx4ovb07ah3wvg 
       foreign key (id_pessoa) 
       references pessoa;

    alter table if exists usuario_funcao_tab 
       add constraint FKok5gp7l6j5dnmnkpwwdwxs374 
       foreign key (id_funcao) 
       references funcao;

    alter table if exists usuario_funcao_tab 
       add constraint FK8fn746xwkm1mlneygai6lnbqk 
       foreign key (id_usuario) 
       references usuario;