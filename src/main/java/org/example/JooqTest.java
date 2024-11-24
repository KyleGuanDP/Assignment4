package org.example;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.example.generated.Tables.AUTHOR;

public class JooqTest {

    // 数据库连接信息
    private static final String URL = "jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres";
    private static final String USER = "postgres.jjxrfxvoadgbjxhpxavd";
    private static final String PASSWORD = "kotzyc-4peBka-viqnuv";

    public static void main(String[] args) throws Exception {

        insertAuthor(5, "John", "Doe");

    }

    /**
     * 更新作者的名字
     *
     * @param id        作者 ID
     * @param firstName 新的名字
     * @throws Exception 异常
     */
    public static void updateAuthor(int id, String firstName) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);

            int rowsAffected = dsl.update(AUTHOR)
                    .set(AUTHOR.FIRST_NAME, firstName)
                    .where(AUTHOR.ID.eq(id))
                    .execute();

            System.out.println("Rows updated: " + rowsAffected);
        }
    }

    /**
     * 插入作者信息
     *
     * @param id        作者 ID
     * @param firstName 名字
     * @param lastName  姓氏
     * @throws Exception 异常
     */
    public static void insertAuthor(int id, String firstName, String lastName) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);

            int rowsAffected = dsl.insertInto(AUTHOR)
                    .set(AUTHOR.ID, id)
                    .set(AUTHOR.FIRST_NAME, firstName)
                    .set(AUTHOR.LAST_NAME, lastName)
                    .execute();

            System.out.println("Rows inserted: " + rowsAffected);
        }
    }

    /**
     * 按名字查询作者信息
     *
     * @param firstName 作者名字
     * @throws Exception 异常
     */
    public static void getAuthorByFirstName(String firstName) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);

            dsl.select(AUTHOR.ID, AUTHOR.LAST_NAME)
                    .from(AUTHOR)
                    .where(AUTHOR.FIRST_NAME.eq(firstName))
                    .fetch()
                    .forEach(record -> {
                        System.out.printf("ID: %d, LastName: %s%n",
                                record.get(AUTHOR.ID),
                                record.get(AUTHOR.LAST_NAME));
                    });
        }
    }
}
