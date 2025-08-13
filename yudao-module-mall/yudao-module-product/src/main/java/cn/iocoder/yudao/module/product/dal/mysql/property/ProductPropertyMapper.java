package cn.iocoder.yudao.module.product.dal.mysql.property;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.product.controller.admin.property.vo.property.ProductPropertyPageReqVO;
import cn.iocoder.yudao.module.product.dal.dataobject.property.ProductPropertyDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductPropertyMapper extends BaseMapperX<ProductPropertyDO> {

    default PageResult<ProductPropertyDO> selectPage(ProductPropertyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductPropertyDO>()
                .likeIfPresent(x -> x.getName(), reqVO.getName())
                .betweenIfPresent(x -> x.getCreateTime(), reqVO.getCreateTime())
                .orderByDesc(x -> x.getId()));
    }

    default ProductPropertyDO selectByName(String name) {
        return selectOne(x -> x.getName(), name);
    }

}
