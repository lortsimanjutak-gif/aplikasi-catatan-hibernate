package com.nasipecel.aplikasicatatan.dao;

import com.nasipecel.aplikasicatatan.entity.Catatan;
import com.nasipecel.aplikasicatatan.entity.UserAccount;
import com.nasipecel.aplikasicatatan.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AplikasiDao {

    public UserAccount login(String email, String passwordHash) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM UserAccount u WHERE u.email = :email AND u.password = :password",
                    UserAccount.class)
                    .setParameter("email", email)
                    .setParameter("password", passwordHash)
                    .uniqueResult();
        }
    }

    public void registrasi(UserAccount user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void tambahCatatan(int userId, String judul, String isi, boolean favorit) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            UserAccount user = session.get(UserAccount.class, userId);
            if (user == null) throw new IllegalStateException("Pengguna tidak ditemukan.");
            session.persist(new Catatan(user, judul, isi, favorit));
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<Catatan> daftarCatatan(int userId, boolean hanyaFavorit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Catatan c WHERE c.user.id = :userId"
                    + (hanyaFavorit ? " AND c.favorit = true" : "")
                    + " ORDER BY c.id DESC";
            return session.createQuery(hql, Catatan.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }

    public List<Catatan> cariCatatan(int userId, String kata) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Catatan c WHERE c.user.id = :userId "
                    + "AND (LOWER(c.judul) LIKE :kata OR LOWER(c.isi) LIKE :kata) "
                    + "ORDER BY c.id DESC", Catatan.class)
                    .setParameter("userId", userId)
                    .setParameter("kata", "%" + kata.toLowerCase() + "%")
                    .getResultList();
        }
    }

    public Catatan ambilCatatan(int id, int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Catatan c WHERE c.id = :id AND c.user.id = :userId",
                    Catatan.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .uniqueResult();
        }
    }

    public void perbaruiCatatan(int id, int userId, String judul, String isi, boolean favorit) {
        jalankanPerubahan(id, userId, catatan -> {
            catatan.setJudul(judul);
            catatan.setIsi(isi);
            catatan.setFavorit(favorit);
        });
    }

    public void hapusCatatan(int id, int userId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Catatan catatan = session.createQuery(
                    "FROM Catatan c WHERE c.id = :id AND c.user.id = :userId",
                    Catatan.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .uniqueResult();
            if (catatan != null) session.remove(catatan);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    private void jalankanPerubahan(int id, int userId, java.util.function.Consumer<Catatan> perubahan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Catatan catatan = session.createQuery(
                    "FROM Catatan c WHERE c.id = :id AND c.user.id = :userId",
                    Catatan.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .uniqueResult();
            if (catatan == null) throw new IllegalArgumentException("Catatan tidak ditemukan.");
            perubahan.accept(catatan);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
