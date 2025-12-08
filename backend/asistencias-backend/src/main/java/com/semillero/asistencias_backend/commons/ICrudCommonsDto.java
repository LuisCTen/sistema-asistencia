package com.semillero.asistencias_backend.commons;

public interface ICrudCommonsDto<DTOReq,DTORes,ID> {
   public DTORes save(DTOReq request);
   public DTORes update(ID id, DTOReq request);
   public DTORes findById(DTOReq request);
   public DTORes delete(ID id);
}
