create extension if not exists pgcrypto;

/*get_salt() - добавляет соль - доп. значение присоединяються к паролю для его усложнения*/
update usr set password = crypt(password, gen_salt('bf', 8));