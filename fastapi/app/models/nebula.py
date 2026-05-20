from sqlalchemy import Column, String, DateTime, Float, Enum
from sqlalchemy.dialects.postgresql import UUID
import uuid
from datetime import datetime
from app.database import Base
from app.schemas.nebula import NebulaStatus

class Nebula(Base):
    __tablename__ = "nebulas"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    title = Column(String, nullable=False)
    description = Column(String)
    color_hex = Column(String, nullable=False)
    target_date = Column(DateTime)
    status = Column(Enum(NebulaStatus), nullable=False)
    progress = Column(Float, default=0.0)
    created_at = Column(DateTime, default=datetime.utcnow)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)
