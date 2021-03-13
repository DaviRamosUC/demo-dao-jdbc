package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO department (Name) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					Integer id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		
		try {
			if (findById(obj.getId()) != null) {
				st = conn.prepareStatement(
						"UPDATE department "
						+ "set Name = ? "
						+ "WHERE Id = ?"
						);
				st.setString(1, obj.getName());
				st.setInt(2, obj.getId());
				
				st.executeUpdate();

			}else {
				throw new DbException("None Department was found with this Id!");
			}			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			
			try {
				if (findById(id) != null) {
					st.setInt(1, id);	
					st.executeUpdate();
					
				}else {
					throw new DbException("None department was find with this Id");
				}			
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}			
			
			
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT department.* from department WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				return instantiateDepartment(rs);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

		return null;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		List<Department> list = new ArrayList<>(); 
		
		try {
			st = conn.prepareStatement("Select * from department");
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Department obj = instantiateDepartment(rs);
				list.add(obj);
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		return list;
	}

}
