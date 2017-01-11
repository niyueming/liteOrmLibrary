# LiteORMLibrary
这只是个Android Studio的Module，不能独立运行。
依赖module [ormLibrary](https://github.com/niyueming/ormLibrary)

### 注解

> 基础注解

> @Table("test_model") 表名
> @PrimaryKey(AssignType.AUTO_INCREMENT) 主键自增长
> @PrimaryKey(AssignType.BY_MYSELF) 自己设置主键
> @Ignore 忽略该字段，不存入数据库
> @Column("login") 指定列名
> @Collate("NOCASE") 大小写无关
> 关系映射：

> @Mapping(Relation.ManyToMany) 多对多
> @Mapping(Relation.OneToMany) 一对多
> @Mapping(Relation.OneToOne) 一对一
> @Mapping(Relation.ManyToOne) 多对一
> @MapCollection(ConcurrentLinkedQueue.class) 指定约束对象的集合类型
> 约束相关：

> @NotNull 非空约束
> @Default("true") 默认约束
> @Check("index > 0 ") check约束
> @Unique 唯一约束
> @UniqueCombine() 联合唯一约束
> 约束冲突：

> ON CONFLICT 子句不是一个单独的 SQL 命令。 它可以出现在很多其它的 SQL 命令中，是一个非标准的子句。ON CONFLICT 子句指定一个用于解决约束冲突的算法。 有五种选择(具体解释请看文末参考链接，copy过来也没啥意思)： 
> - @Conflict(Strategy.ROLLBACK) 
> - @Conflict(Strategy.ABORT) 
> - @Conflict(Strategy.FAIL) 
> - @Conflict(Strategy.IGNORE) 
> - @Conflict(Strategy.REPLACE)

> 这里总结了LiteOrm使用的几乎所有的注解，全部从samples中总结出来。有些一看就懂，但有些就比较生疏。但框架毕竟是框架，用着用着就熟悉了。从注解也可以看出来该框架确实比较全面。为了弄懂一些东西，又复习了一遍Sql语句。因为它的查询某些语法完全和SQL语句一样。下面就介绍一下他的基本CRUD操作。

